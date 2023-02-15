package util;

public class Utility {
	public static String arrow(int depth){
		String img = "<img src='./arrow.png' width='20px' heigth='20px' />";
		String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";
		
		String ts = "";
		for(int i = 0; i < depth; i++){
			ts += nbsp;    // depth에 따라 띄어쓰기 변화
		}
		
		return depth==0?"":ts + img;	// 기본글(부모글)
	}
}
