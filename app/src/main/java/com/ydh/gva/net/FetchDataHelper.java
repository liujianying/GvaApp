package com.ydh.gva.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FetchDataHelper {


    private static ExecutorService fetchDataThreadpool = Executors.newFixedThreadPool(5);


    public static void post(String url, String body, DataFetcherRunnable.IFetchCallback callback) {

        DataFetcherRunnable fetch = new DataFetcherRunnable();
        fetch.setUrl(url);
        fetch.setBody(body);
        fetch.setCallback(callback);
        fetchDataThreadpool.execute(fetch);

    }





//	public static void postPic(final String url, final String body, IFetchCallback_test callback) {
//
//		new Thread(){
//
//			@Override
//			public void run() {
//				super.run();
//				try {
//					String str = JsonFetcher.postPic(url, body);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//
//		}.start();
//	}

}
