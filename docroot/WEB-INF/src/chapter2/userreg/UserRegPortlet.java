package chapter2.userreg;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class UserRegPortlet extends GenericPortlet {
	
	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";
	private static final String REG_ACTION_STATUS = "regActionStatus";
	private static final String REGISTER_ACTION = "registerAction";
	private String defaultEmail;

	@Override
	public void init() throws PortletException {
		super.init();
		this.defaultEmail = getPortletConfig().getInitParameter("defaultEmail");
	}
	
	@RenderMode(name = "VIEW")
	public void renderRegForm(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		
		String regActionStatus = (String) request.getAttribute(REG_ACTION_STATUS);
		
		if ( regActionStatus == null) {
			System.out.println("Call Render No Action");
			createRegPage(request, response);
		}else {
			System.out.println("Call Render After registerAction, status: " + regActionStatus);
			switch (regActionStatus) {
			case SUCCESS:
				createSuccessPage(request, response);
				break;
			case FAIL:
				createRegPage(request, response);
				break;
			default:
				break;
			}
		}
	}
	
	@ProcessAction(name = REGISTER_ACTION)
	public void registerAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {		
		System.out.println("Proccess Action...");

		User user = new User(request.getParameter("firstNameTxt"),
				request.getParameter("lastNameTxt"),
				request.getParameter("emailTxt"));
		
		if (user.getEmail() != null && !user.getEmail().isEmpty()) {
			request.setAttribute(REG_ACTION_STATUS, SUCCESS);
			saveToDb(user);
			request.setAttribute("user", user);
		}else {
			request.setAttribute(REG_ACTION_STATUS, FAIL);
			request.setAttribute("errorMsg",
					getPortletConfig().getResourceBundle(request.getLocale())
							.getString("form.error.missingEmail"));
		}
	}
	
	@RenderMode(name = "EDIT")
	public void renderPref(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		getPortletContext().getRequestDispatcher("/WEB-INF/jsp/pref.jsp")
				.include(request, response);
	}
	
	@RenderMode(name = "HELP")
	public void renderHelp(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		getPortletContext().getRequestDispatcher("/WEB-INF/jsp/help.jsp")
				.include(request, response);
	}

	private void createSuccessPage(RenderRequest request,
			RenderResponse response) throws PortletException, IOException {
		
		request.setAttribute("homeRenderUrl", response.createRenderURL());
		getPortletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp")
				.include(request, response);
	}

	private void createRegPage(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		
		request.setAttribute("defaultEmail", defaultEmail);
		
		// Create Portlet URLs to be used in the render view
		PortletURL registerActionUrl = response.createActionURL();
		registerActionUrl.setParameter(ActionRequest.ACTION_NAME, REGISTER_ACTION);
		
		PortletURL homeRenderUrl = response.createRenderURL();
		
		request.setAttribute("registerActionUrl", registerActionUrl);
		request.setAttribute("homeRenderUrl", homeRenderUrl);
		
		getPortletContext().getRequestDispatcher("/WEB-INF/jsp/regForm.jsp")
				.include(request, response);
	}
	
	private void saveToDb(User user) {
		System.out.println("User: " + user + " saved to DB");
	}

}
