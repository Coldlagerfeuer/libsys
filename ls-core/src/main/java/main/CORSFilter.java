package main;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * CORSFilter to allow json calls from external resources.
 * 
 * @author Nils Frantzen <n.frantzen@bauer-kirch.de>
 *
 */
@Component
public class CORSFilter implements Filter {

	/**
	 * Sets the required response header fields to control remote calls from
	 * external resources.
	 */
	// TODO check origin, check max age, check allow headers
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept");

		chain.doFilter(req, res);
	}

	/**
	 * no implementation needed for our requirement
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		// nop
	}

	/**
	 * no implementation needed for our requirement
	 */
	@Override
	public void destroy() {
		// nop
	}

}