package com.xyh.video.utils;

import java.util.Random;

public class FullAdUtil {
	

	public static boolean isOpenFullAd(int offset){
		if(offset == -1){
			return false;
		}
		Random random=new Random(System.currentTimeMillis());
		int rInt=random.nextInt(100);
		if(rInt<=offset){
			return true;
		}
		return false;
		
	}
	public static void main(String[] args) {
		System.out.println(isOpenFullAd(1));
	}
}
