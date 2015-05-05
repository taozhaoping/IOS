package com.zxing.executor;

public final class AsyncTaskExecManager extends PlatformSupportManager<AsyncTaskExecInterface> {

	public AsyncTaskExecManager() {
		super(AsyncTaskExecInterface.class, new DefaultAsyncTaskExecInterface());
	}

}
