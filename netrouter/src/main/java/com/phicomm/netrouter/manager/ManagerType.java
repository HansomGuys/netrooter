package com.phicomm.netrouter.manager;

public enum ManagerType {
	ONLINE_NOTIFY, 
	BWINFO_REPORT, 
	TOPOINFO_REPORT, 
	DEVICE_WARNING, 
	UPDATE_TRANRES, 
	ONLINE_KEEP;

	/*private int _type;
	ManagerType(int type) {
		this._type = type;
	}
	
	@Override
	public String toString() {
		return ""+_type;
	}*/
}
