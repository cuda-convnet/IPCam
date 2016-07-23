package com.ipcam.internalevent;

import android.util.Log;

import com.ipcam.asyncio.IDataToSendTreatment;
import com.ipcam.asyncio.ISender;
import com.ipcam.helper.AsyncExecutor;

public class FunctionsForInternalEvent<TDataToSend> implements IDataToSendTreatment<TDataToSend>
{
	private final String TAG = "AsyncMessageSender";
	private AsyncExecutor<IInternalEventInfo> resultHandler = null;

	public FunctionsForInternalEvent(AsyncExecutor<IInternalEventInfo> r)
	{
		resultHandler = r;
	}
	@Override
	public void reportResult(TDataToSend data, ISender.SEND_RESULT sendingResult)
	{
		if (resultHandler != null)
		{
			IInternalEventInfo result = (IInternalEventInfo) data;
	
	    	if (sendingResult == ISender.SEND_RESULT.SUCCESS)
	    		result.setParameter(1);
	    	else
	    		result.setParameter(0);
	
	    	result.setInternalEvent(InternalEvent.TASK_COMPLETE);
	    	resultHandler.executeAsync(result);
		}
		else
		{
			Log.e(TAG, "ResultReporterForInternalEvent: reportResult error resultHandler is null");
		}
	}
	@Override
	public void addString(TDataToSend data, String str)
	{
		IInternalEventInfo result = (IInternalEventInfo) data;
		result.concatToMessage(str);
	}
    
}