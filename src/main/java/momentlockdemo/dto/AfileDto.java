package momentlockdemo.dto;

import momentlockdemo.entity.Afile;

public class AfileDto {
	
//	프론트에서 afileService.getAfilesByCapsule()로 하면 
//	Capsule <=> Afile 순환참조 에러 발생..
//	그래서 필요한 것만 담은 AfileDto 클래스를 만듦.
	
	private Afile afcname;
	private Afile afcontenttype;

}
