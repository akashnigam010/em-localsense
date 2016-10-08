package in.cw.sense.app.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import in.cw.sense.app.services.BillSynchService;

public class BillSynchJob {
	
	@Autowired
	BillSynchService billService;

	@Scheduled(fixedDelay = 15000)
	public void synchBillFromLocalToCloud() {
		billService.syncBillToCloud();
	}
}
