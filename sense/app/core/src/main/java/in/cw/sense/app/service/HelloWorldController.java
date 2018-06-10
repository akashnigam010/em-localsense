
package in.cw.sense.app.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import in.cw.sense.app.services.BillSynchService;
import in.cw.sense.app.services.CloudConnectService;
import in.cw.sense.app.services.CloudConnectServiceImpl;

@Controller
public class HelloWorldController {
	private static final Logger LOG = Logger.getLogger(HelloWorldController.class);
	
	@Autowired
	BillSynchService billSynchService;// = new BillSynchServiceImpl();
	
	//@Autowired
	CloudConnectService cloudConnect  = new CloudConnectServiceImpl();
	

	@RequestMapping("/helloworld")
	public ModelAndView hello() {

		String helloWorldMessage = "Hello world from java2blog!";
		return new ModelAndView("hello", "message", helloWorldMessage);
	}

	@RequestMapping(value = "/sendBill", method = RequestMethod.POST)
	public String test() {

		sendBills();
		return "index";
	}

	@RequestMapping(value = "/handShake", method = RequestMethod.POST)
	public String initiateHandShake() {

		cloudConnect.connect();
		return "sendBillPage";
	}



	private void sendBills() {
		/*LSClient lsClient;
		try {
			lsClient = LSClient.getInstance();
			if(lsClient.isSessionOpen()) {
				lsClient.sendMessage(getBillMessage());
			} else {
				System.out.println("Session is close now, cant send message to server..");
			}
		} catch (DeploymentException | IOException | URISyntaxException | BusinessException e) {
			e.printStackTrace();
		}*/
		
		billSynchService.syncBillToCloud();
	}

	/*private String getBillMessage() {
		Message message = new Message();
		message.setType(MessageElementType.BILL_DETAIL);
		BillDetailMessageElement messageElement = new BillDetailMessageElement(getBills());
		message.setMessageElement(messageElement);
		
		return  JsonUtil.toJson(message);
	}*/

	/*private List<BillDto> getBills() {
		File folder = new File("C:\\cantwait\\bills");
		List<BillDto> bills = new ArrayList<>();
		
		Gson gson = new Gson();
		
		for(File file :  folder.listFiles()) {
			BillDto billDto;
			try {
				
				billDto = gson.fromJson(new FileReader(file), BillDto.class);
				bills.add(billDto);
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return bills;
	}*/
	
	

}
