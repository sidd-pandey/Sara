package com.herokuapp.darkfire.sara.objects.factories;

import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.Bill;
import com.herokuapp.darkfire.sara.objects.BillingUpdate;
import com.herokuapp.darkfire.sara.objects.Complaint;
import com.herokuapp.darkfire.sara.objects.ComplaintStatus;
import com.herokuapp.darkfire.sara.objects.CustomerCareNumber;
import com.herokuapp.darkfire.sara.objects.DataPlan;
import com.herokuapp.darkfire.sara.objects.Grievance;
import com.herokuapp.darkfire.sara.objects.Introduction;
import com.herokuapp.darkfire.sara.objects.ModelObject;
import com.herokuapp.darkfire.sara.objects.SpeedTest;
import com.herokuapp.darkfire.sara.objects.TRAINews;
import com.herokuapp.darkfire.sara.objects.TSPNews;
import com.herokuapp.darkfire.sara.objects.USSDCodes;
import com.herokuapp.darkfire.sara.objects.VAS;
import com.herokuapp.darkfire.sara.objects.VASUpdate;

public class ModelObjectFactory {

	public enum ObjectList{
		BILL,
		BILL_UPDATE,
		COMPLAINT,
		COMPLAINT_STATUS,
		CUSTOMER_CARE_NUMBER,
		DATA_PLAN,
		GRIEVANCE,
		INTRODUCTION,
		TRAI_NEWS,
		TSP_NEWS,
		USSD_CODES,
		VAS,
		VAS_UPDATE,
		SPEED_TEST;
	}
	
	public static ModelObject createModelObject(ObjectList obj, Machine machine){
		
		switch(obj){
			case BILL:
				return new Bill(machine);
			case BILL_UPDATE:
				return new BillingUpdate(machine);
			case COMPLAINT:
				return new Complaint(machine);
			case COMPLAINT_STATUS:
				return new ComplaintStatus(machine);
			case CUSTOMER_CARE_NUMBER:
				return new CustomerCareNumber(machine);
			case DATA_PLAN:
				return new DataPlan(machine);
			case GRIEVANCE:
				return new Grievance(machine);
			case INTRODUCTION:
				return new Introduction(machine);
			case TRAI_NEWS:
				return new TRAINews(machine);
			case TSP_NEWS:
				return new TSPNews(machine);
			case USSD_CODES:
				return new USSDCodes(machine);
			case VAS:
				return new VAS(machine);
			case VAS_UPDATE:
				return new VASUpdate(machine);
			case SPEED_TEST:
				return new SpeedTest(machine);
			default: return null;
		}
	}
}
