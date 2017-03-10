
//var serverUrl = "http://localhost:9000/"
var serverUrl = "http://" + location.host + ":8080/";


//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
//Sets the min-height of #page-wrapper to window size
$(function() {

	$('#menu').load('menu.html', function() {
		$('#side-menu').metisMenu();
	});
	
	/*
	 * Search-field functionality start
	 */
	$(document).on('click', '#SearchButton', function(){
		search($("#SearchField").val());
    });

	$(document).on('keyup', '#SearchField', function(e){
	    if (e.which == 13) {
	    	search($(this).val());
	    } else {
	    	console.log(booknames);
	   	 $("#SearchField").autocomplete({
	   		source: booknames 
	   	 });
	    	
	    }
	});
	/*
	 * Search-field functionality end
	 */
	
	/*
	 * File Upload. Usage with  $(':file').on('fileselect', function(event, numFiles, label) {CODE}
	 */
	$(document).on('change', ':file', function(input) {
		console.log("FILE UPLOAD");
		console.log(input);
		
		// Show uploaded Files
		var files = input.target.files; 
		console.log(files);
		console.log(files[0]);
		console.log(files[0].name);
		var output = [];
		for (var i = 0, f; f = files[i]; i++) {
			output.push('<li><strong>', f.name, '</strong> (', f.type || 'n/a', ') - ',
				f.size, ' bytes</li>');
		}
		console.log(output.join(''));
		$('#file-list').html('<ul>' + output.join('') + '</ul>');
		sendFilesToRestApi(files);
	});

	$(window)
			.bind(
					"load resize",
					function() {
						var topOffset = 50;
						var width = (this.window.innerWidth > 0) ? this.window.innerWidth
								: this.screen.width;
						if (width < 768) {
							$('div.navbar-collapse').addClass('collapse');
							topOffset = 100; // 2-row-menu
						} else {
							$('div.navbar-collapse').removeClass('collapse');
						}

						var height = ((this.window.innerHeight > 0) ? this.window.innerHeight
								: this.screen.height) - 1;
						height = height - topOffset;
						if (height < 1)
							height = 1;
						if (height > topOffset) {
							$("#page-wrapper").css("min-height",
									(height) + "px");
						}
					});

	var url = window.location;
	var element = $('ul.nav a').filter(function() {
		return this.href == url;
	}).addClass('active').parent();

	while (element.is('li')) {
		element = element.parent().addClass('in').parent();
	}
});

/**
 * Get value of the given query param
 * 
 * @param variable
 *            id of the query param
 * @returns the value of the given param, if the param is not present
 *          <code>false</code> is returned
 */
function getQueryVariable(variable) {
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		if (pair[0] == variable) {
			return pair[1].replaceAll("%20"," ");
		}
	}
	return null;
}

function search(searchText) {
	// If searchText = bookname -> open book website, else site with search results
	$.ajax({
		url : serverUrl + "books/getByName?name=" + searchText
    }).then(function(data) {
    	if (data.length == 1) {
    		// Buch mit identischem Namen gefunden
    		console.log("PONG");
    		
    	} else {
    		// Mehrere/keine Bücher mit dem Namen gefunden
    		url = "list-books.html?name=" + searchText;
    	    window.location.href = url;
    	}
//	}, function(){
//		url = "list-books.html?name=" + searchText;
//	    window.location.href = url;
	});
}

function sendFilesToRestApi(files) {
	// Start ajax request for every file
	for (let f of files) {
		console.log(f);
		console.log(f.name);
		console.log(JSON.stringify(f));
		var formData = new FormData();
		formData.append('file', f);
		$.ajax({
			type : 'POST',
			data : formData,
			processData: false,  // tell jQuery not to process the data
			contentType: false,  // tell jQuery not to set contentType
			url: serverUrl + "books/readBibtexFile",
			success: function(data) {
				console.log(data);
				if (data !== null) {
					console.log("Successfully saved books in database.");
					$("#alert-div").append(getSuccessAlertText("Successfully send bibtex file to server.</br>" +
							"Added " + data[1] + " new books from " + data[0] + " recognized entries."));
				} else {
					console.log("Could not read file.");
					$("#alert-div").append(getErrorAlertText("Could not read bibtex file."));
				}
			},
			error: function(e) {
				console.log("Could not send book.");
				$("#alert-div").append(getErrorAlertText("Could not send bibtex file."));
			}
		});
		
		
	}
}

// Attribute name from every object in array
function getNameStringFromArray(authors, placeholder) {
	var result = "";
	for (var i in authors) {
		var author = authors[i];
		console.log(author);
		result = result + author.name + placeholder;
	}
	console.log(result);
	result = result.substring(0, result.length - placeholder.length);
	console.log(result);
	return result;
}

function getErrorAlertText(text) {
	/* setTimeout(function() {
	    $('.alert').fadeOut('slow');
	    }, 10000); */ 
	    // Erros should not close self.
	return '<div id="error-alert" class="alert alert-danger alert-dismissable fade in">'
    + '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
    + '<strong>Error!</strong> ' + text + '</div>';
}

function getSuccessAlertText(text) {
	setTimeout(function() {
	    $('.alert').fadeOut('slow');
	    }, 6000);
	return '<div id="success-alert" class="alert alert-success alert-dismissable fade in">'
    + '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
    + '<strong>Success!</strong> ' + text + '</div>';
}

function getInfoAlertText(text) {
	return '<div id="info-alert class="alert alert-info alert-dismissable fade in">'
    + '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
    + '<strong>Info!</strong> ' + text + '</div>';
}



String.prototype.replaceAll = function(search, replace){
    //if replace is null, return original string otherwise it will
    //replace search string with 'undefined'.
    if (replace === undefined) {
        return this.toString();
    }
    return this.replace(new RegExp(search, 'g'), replace);
};
