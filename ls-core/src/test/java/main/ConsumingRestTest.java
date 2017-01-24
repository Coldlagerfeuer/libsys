/**
 * 
 */
package main;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class ConsumingRestTest {

	@Test
	public void testConsumingSpringExample() {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        System.out.println(quote.toString());
	}

}
