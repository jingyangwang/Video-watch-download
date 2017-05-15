package com.xyh.video.utils;

import java.util.ArrayList;
import java.util.List;


public class CreateMap {
	private static List<Country> allcountries = CreateMap.createMap();
	
	
	public static List<Country> getCountryList(){
		if(allcountries==null){
			allcountries=CreateMap.createMap();
		}
		return allcountries;
	}
	
	private static List<Country> createMap() {
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1,460,"CN","zh","中华人民共和国","People s Republic of China","东亚"));
		countries.add(new Country(2,440,"JP","ja","日本国","Japan","东亚"));
		countries.add(new Country(3,467,"KP","ko","朝鲜民主主义人民共和国","Democratic Peoples Republic of Korea","东亚"));
		countries.add(new Country(4,450,"KR","ko","大韩民国","Republic of Korea","东亚"));
		countries.add(new Country(5,428,"MN","mn","蒙古国","Mongolia","东亚"));
		countries.add(new Country(6,454,"HK","zh","中华人民共和国香港特别行政区","Hong Kong Special Administrative Region of the Peo","东亚"));
		countries.add(new Country(7,455,"MO","zh","中华人民共和国澳门特别行政区","Macau","东亚"));
		countries.add(new Country(8,466,"TW","zh","台湾省","Taiwan Province","东亚"));
		countries.add(new Country(9,452,"VN","vi","越南社会主义共和国","Socialist Republic of Vietnam","东南亚"));
		countries.add(new Country(10,520,"TH","th","泰王国","Kingdom of Thailand","东南亚"));
		countries.add(new Country(11,502,"MY","ms","马来西亚","Malaysia","东南亚"));
		countries.add(new Country(12,510,"ID","id","印度尼西亚共和国","The Republic of Indonesia","东南亚"));
		countries.add(new Country(13,515,"PH","tl","菲律宾共和国","Republic of the Philippines","东南亚"));
		countries.add(new Country(14,525,"SG","en","新加坡共和国","Republic of Singapore","东南亚"));
		countries.add(new Country(15,457,"LA","lo","老挝人民民主共和国","Lao People s Democratic Republic","东南亚"));
		countries.add(new Country(16,528,"BN","ms","文莱达鲁萨兰国","Brunei","东南亚"));
		countries.add(new Country(17,456,"KH","km","柬埔寨王国","Kingdom of Cambodia","东南亚"));
		countries.add(new Country(18,414,"MM","my","缅甸联邦共和国","Republic of the Union of Myanmar","东南亚"));
		countries.add(new Country(19,514,"TL","pt","东帝汶民主共和国","DEMOCRATIC REPUBLIC OF TIMOR-LESTE","东南亚"));
		countries.add(new Country(20,470,"BD","en","孟加拉人民共和国","People s Republic of Bangladesh","南亚"));
		countries.add(new Country(21,402,"BT","dz","不丹王国","Kingdom of Bhutan","南亚"));
		countries.add(new Country(22,404,"IN","en","印度共和国","Republic of India","南亚"));
		countries.add(new Country(23,405,"IN","en","印度共和国","Republic of India","南亚"));
		countries.add(new Country(24,472,"MV","dv","马尔代夫共和国","The Republic of Maldives","南亚"));
		countries.add(new Country(25,429,"NP","ne","尼泊尔联邦民主共和国","Federal Democratic Republic of Nepal","南亚"));
		countries.add(new Country(26,413,"LK","si","斯里兰卡民主社会主义共和国","The Democratic Socialist Republic of Sri Lanka","南亚"));
		countries.add(new Country(27,410,"PK","ur","巴基斯坦伊斯兰共和国","the Islamic Republic of Pakistan","南亚"));
		countries.add(new Country(28,712,"CR","es","哥斯达黎加共和国","Republic of Costa Rica","北美"));
		countries.add(new Country(29,310,"US","en","美利坚合众国","The United States of America","北美"));
		countries.add(new Country(30,311,"US","en","美利坚合众国","The United States of America","北美"));
		countries.add(new Country(31,316,"US","en","美利坚合众国","The United States of America","北美"));
		countries.add(new Country(32,302,"CA","en","加拿大","Canada","北美"));
		countries.add(new Country(33,332,"MX","es","墨西哥合众国","The?United States of Mexico","北美"));
		countries.add(new Country(34,708,"HN","es","洪都拉斯共和国","Republic of Honduras","北美"));
		countries.add(new Country(35,704,"GT","es","危地马拉共和国","The Republic of Guatemala","北美"));
		countries.add(new Country(36,368,"CU","es","古巴共和国","The Republic of Cuba","北美"));
		countries.add(new Country(37,364,"BS","en","巴哈马国","The Commonwealth of The Bahamas","北美"));
		countries.add(new Country(38,714,"PA","es","巴拿马共和国","The Republic of Panama","北美"));
		countries.add(new Country(39,338,"JM","en","牙买加","Jamaica","北美"));
		countries.add(new Country(40,372,"HT","fr","海地共和国","The Republic of Haiti","北美"));
		countries.add(new Country(41,702,"BZ","en","伯利兹","Belize","北美"));
		countries.add(new Country(42,342,"BB","en","巴巴多斯","Barbados","北美"));
		countries.add(new Country(43,352,"GD","en","格林纳达","Grenada","北美"));
		countries.add(new Country(44,710,"NI","es","尼加拉瓜共和国","The Republic of Nicaragua","北美"));
		countries.add(new Country(45,366,"DM","en","多米尼克国","The Commonwealth of Dominica","北美"));
		countries.add(new Country(46,370,"DO","es","多米尼加共和国","The Dominican Republic","北美"));
		countries.add(new Country(47,374,"TT","en","特立尼达和多巴哥共和国","Republic of Trinidad and Tobago","北美"));
		countries.add(new Country(48,330,"PR","es","波多黎各自由邦","The Commonwealth of Puerto Rico","北美"));
		countries.add(new Country(49,722,"AR","es","阿根廷共和国","The Republic of Argentina","南美"));
		countries.add(new Country(50,724,"BR","pt","巴西联邦共和国","The Federative Republic of Brazil","南美"));
		countries.add(new Country(51,736,"BO","es","多民族玻利维亚国","The Multinational States of Bolivia","南美"));
		countries.add(new Country(52,730,"CL","es","智利共和国","Republic of Chile","南美"));
		countries.add(new Country(53,732,"CO","es","哥伦比亚共和国","The Republic of Colombia","南美"));
		countries.add(new Country(54,740,"EC","es","厄瓜多尔共和国","The Republic of Ecuador","南美"));
		countries.add(new Country(55,738,"GY","en","圭亚那共和国","The Republic of Guyana","南美"));
		countries.add(new Country(56,744,"PY","es","巴拉圭共和国","The Republic of Paraguay","南美"));
		countries.add(new Country(57,716,"PE","es","秘鲁共和国","The Republic of Peru","南美"));
		countries.add(new Country(58,746,"SR","nl","苏里南共和国","The Republic of Suriname","南美"));
		countries.add(new Country(59,748,"UY","es","乌拉圭东岸共和国","The Oriental Republic of Uruguay","南美"));
		countries.add(new Country(60,734,"VE","es","委内瑞拉玻利瓦尔共和国","Bolivarian Republic of Venezuela","南美"));
		countries.add(new Country(61,602,"EG","ar","阿拉伯埃及共和国","The Arab Republic of Egypt","北非"));
		countries.add(new Country(62,634,"SD","ar","苏丹共和国","The Republic of Sudan","北非"));
		countries.add(new Country(63,606,"LY","ar","利比亚国","State of Libya","北非"));
		countries.add(new Country(64,605,"TN","ar","突尼斯共和国","The Republic of Tunisia","北非"));
		countries.add(new Country(65,603,"DZ","ar","阿尔及利亚民主人民共和国","People s Democratic Republic of Algeria","北非"));
		countries.add(new Country(66,604,"MA","ar","摩洛哥王国","The Kingdom of Morocco","北非"));
		countries.add(new Country(67,609,"MR","ar","毛里塔尼亚伊斯兰共和国","The Islamic Republic of Mauritania","西非"));
		countries.add(new Country(68,608,"SN","fa","塞内加尔共和国","the Republic of Senegal","西非"));
		countries.add(new Country(69,607,"GM","en","冈比亚共和国","The Republic of the Gambia","西非"));
		countries.add(new Country(70,610,"ML","fa","马里共和国","The Republic of Mali","西非"));
		countries.add(new Country(71,613,"BF","fa","布基纳法索","Burkina Faso","西非"));
		countries.add(new Country(72,611,"GN","fa","几内亚共和国","The Republic of Guinea","西非"));
		countries.add(new Country(73,632,"GW","pt","几内亚比绍共和国","The Republic of Guinea-Bissau","西非"));
		countries.add(new Country(74,625,"CV","pt","佛得角共和国","The Republic of Cape Verde","西非"));
		countries.add(new Country(75,619,"SL","en","塞拉利昂共和国","The Republic of Sierra Leone","西非"));
		countries.add(new Country(76,618,"LR","en","利比里亚共和国","The Republic of Liberia","西非"));
		countries.add(new Country(77,612,"CI","fa","科特迪瓦共和国","Ivory Coast","西非"));
		countries.add(new Country(78,620,"GH","en","加纳共和国","The Republic of Ghana","西非"));
		countries.add(new Country(79,615,"TG","fa","多哥共和国","The Republic of Togo, La Republique Togolaise","西非"));
		countries.add(new Country(80,616,"BJ","fa","贝宁共和国","The Republic of Benin","西非"));
		countries.add(new Country(81,614,"NE","fa","尼日尔共和国","The Republic of Niger","西非"));
		countries.add(new Country(82,621,"NG","en","尼日利亚联邦共和国","Federal Republic of Nigeria","西非"));
		countries.add(new Country(83,636,"ET","am","埃塞俄比亚联邦民主共和国","The Federal Democratic Republic of Ethiopia","东非"));
		countries.add(new Country(84,639,"KE","sw","肯尼亚共和国","The Republic of Kenya","东非"));
		countries.add(new Country(85,640,"TZ","sw","坦桑尼亚联合共和国","The United Republic of Tanzania","东非"));
		countries.add(new Country(86,641,"UG","en","乌干达共和国","The Republic of Uganda","东非"));
		countries.add(new Country(87,635,"RW","en","卢旺达共和国","The Republic of Rwanda","东非"));
		countries.add(new Country(88,633,"SC","fa","塞舌尔共和国","Republic of Seychelles","东非"));
		countries.add(new Country(89,638,"DJ","fa","吉布提共和国","The Republic of Djibouti","东非"));
		countries.add(new Country(90,637,"SO","ar","索马里联邦共和国","The Somalia Democratic Republic","东非"));
		countries.add(new Country(91,657,"ER","ar","厄立特里亚国","The Commonwealth of eritrea","东非"));
		countries.add(new Country(92,622,"TD","fa","乍得共和国","The Republic of Chad","中非"));
		countries.add(new Country(93,623,"CF","fa","中非共和国","The Central African Republic","中非"));
		countries.add(new Country(94,624,"CM","fa","喀麦隆共和国","Republic of Cameroon","中非"));
		countries.add(new Country(95,627,"GQ","es","赤道几内亚共和国","The Republic of Equatorial Guinea","中非"));
		countries.add(new Country(96,628,"GA","fa","加蓬共和国","The Gabonese Republic","中非"));
		countries.add(new Country(97,629,"CG","fa","刚果共和国","Republic of the Congo","中非"));
		countries.add(new Country(98,630,"CD","fa","刚果民主共和国","Democratic Republic of the Congo","中非"));
		countries.add(new Country(99,645,"ZM","en","赞比亚共和国","The Republic of Zambia","南非"));
		countries.add(new Country(100,631,"AO","pt","安哥拉共和国","The Republic of Angola","南非"));
		countries.add(new Country(101,648,"ZW","en","津巴布韦共和国","Republic of Zimbabwe","南非"));
		countries.add(new Country(102,655,"ZA","en","南非共和国","The Republic of South Africa","南非"));
		countries.add(new Country(103,617,"MU","en","毛里求斯共和国","The Republic of Mauritius","南非"));
		countries.add(new Country(104,649,"NA","en","纳米比亚共和国","The Republic of Namibia","南非"));
		countries.add(new Country(105,643,"MZ","pt","莫桑比克共和国","The Republic of Mozambique","南非"));
		countries.add(new Country(106,650,"MW","en","马拉维共和国","The Republic of Malawi","南非"));
		countries.add(new Country(107,653,"SZ","en","斯威士兰王国","The Kingdom of Swaziland","南非"));
		countries.add(new Country(108,646,"MG","fa","马达加斯加共和国","Madagascar","南非"));
		countries.add(new Country(109,654,"KM","fa","科摩罗联盟","Union of Comoros","南非"));
		countries.add(new Country(110,651,"LS","en","莱索托王国","The Kingdom of Lesotho","南非"));
		countries.add(new Country(111,240,"SE","sv","瑞典王国","The Kingdom of Sweden","北欧"));
		countries.add(new Country(112,244,"FI","fi","芬兰共和国","The Republic of Finland","北欧"));
		countries.add(new Country(113,242,"NO","nb","挪威王国","The Kingdom of Norway，Kongeriket Norge","北欧"));
		countries.add(new Country(114,238,"DK","da","丹麦王国","The Kingdom of Denmark","北欧"));
		countries.add(new Country(115,274,"IS","is","冰岛共和国","The Republic of Iceland","北欧"));
		countries.add(new Country(116,234,"UK","en","大不列颠及北爱尔兰联合王国","The United Kingdom of Great Britain and Northern I","西欧"));
		countries.add(new Country(117,235,"UK","en","大不列颠及北爱尔兰联合王国","The United Kingdom of Great Britain and Northern I","西欧"));
		countries.add(new Country(118,272,"IE","en","爱尔兰共和国","The Republic of Ireland","西欧"));
		countries.add(new Country(119,204,"NL","nl","尼德兰王国","The Kingdom of Netherlands","西欧"));
		countries.add(new Country(120,206,"BE","nl","比利时王国","The Kingdom Of Belgium","西欧"));
		countries.add(new Country(121,270,"LU","fa","卢森堡大公国","The Grand Duchy of Luxembourg","西欧"));
		countries.add(new Country(122,208,"FR","fa","法兰西共和国","The Republic of France","西欧"));
		countries.add(new Country(123,234,"FR","fa","法兰西共和国","The Republic of France","西欧"));
		countries.add(new Country(124,212,"MC","fa","摩纳哥亲王国","The Principality of Monaco","西欧"));
		countries.add(new Country(125,250,"RU","ru","俄罗斯联邦","Russian Federation","东欧"));
		countries.add(new Country(126,248,"EE","et","爱沙尼亚共和国","Republic of Estonia","东欧"));
		countries.add(new Country(127,247,"LV","lv","拉脱维亚共和国","Republic of Latvia","东欧"));
		countries.add(new Country(128,246,"LT","it","立陶宛共和国","The Republic of Lithuania","东欧"));
		countries.add(new Country(129,255,"UA","uk","乌克兰","Ukraine","东欧"));
		countries.add(new Country(130,257,"BY","ru","白俄罗斯共和国","The Republic of Belarus","东欧"));
		countries.add(new Country(131,259,"MD","ro","摩尔多瓦共和国","The Republic of Moldova","东欧"));
		countries.add(new Country(132,260,"PL","pl","波兰共和国","The Republic of Poland","中欧"));
		countries.add(new Country(133,262,"DE","de","德意志联邦共和国","The Federal Republic of Germany","中欧"));
		countries.add(new Country(134,228,"CH","de","瑞士联邦","Swiss Confederation","中欧"));
		countries.add(new Country(135,232,"AT","de","奥地利共和国","The Republic Of Austria","中欧"));
		countries.add(new Country(136,216,"HU","hu","匈牙利","Hungary","中欧"));
		countries.add(new Country(137,230,"CZ","cs","捷克共和国","The Czech Republic","中欧"));
		countries.add(new Country(138,231,"SK","sk","斯洛伐克共和国","The Slovak Republic","中欧"));
		countries.add(new Country(139,295,"LI","de","列支敦士登公国","Principality of Liechtenstein","中欧"));
		countries.add(new Country(140,214,"ES","es","西班牙王国","The Kingdom of Spain","南欧"));
		countries.add(new Country(141,268,"PT","pt","葡萄牙共和国","Portugal,the Portuguese Republic","南欧"));
		countries.add(new Country(142,222,"IT","it","意大利共和国","The Republic of Italy","南欧"));
		countries.add(new Country(143,226,"RO","ro","罗马尼亚","Romania","南欧"));
		countries.add(new Country(144,284,"BG","bg","保加利亚共和国","The Republic of Bulgaria","南欧"));
		countries.add(new Country(145,220,"RS","sr","塞尔维亚共和国","The Republic of Serbia","南欧"));
		countries.add(new Country(146,219,"HR","hr","克罗地亚共和国","The Republic of Croatia","南欧"));
		countries.add(new Country(147,293,"SI","sl","斯洛文尼亚共和国","The Republic of Slovenia","南欧"));
		countries.add(new Country(148,202,"GR","el","希腊共和国","The Hellenic Republic","南欧"));
		countries.add(new Country(149,276,"AL","sq","阿尔巴尼亚共和国","The Republic of Albania","南欧"));
		countries.add(new Country(150,297,"ME","sr","黑山共和国","The Republic of Montenegro","南欧"));
		countries.add(new Country(151,294,"MK","mk","马其顿共和国","The Republic of Macedonia","南欧"));
		countries.add(new Country(152,292,"SM","it","圣马力诺共和国","The Republic of San Marino","南欧"));
		countries.add(new Country(153,278,"MT","en","马耳他共和国","Republic of Malta","南欧"));
		countries.add(new Country(154,213,"AD","bg","安道尔公国","The Principality of Andorra","南欧"));
		countries.add(new Country(155,417,"SY","ar","叙利亚","Syrian Arab Republic","中东"));
		countries.add(new Country(156,418,"IQ","ar","伊拉克","The Republic of Iraq","中东"));
		countries.add(new Country(157,425,"IL","ar","以色列","Israel","中东"));
		countries.add(new Country(158,424,"AE","ar","阿联酋","United Arab Emirates","中东"));
		countries.add(new Country(159,286,"TR","tr","土耳其","Turkey","中亚"));
		countries.add(new Country(160,334,"MX","en","墨西哥","Mexico","北美"));
		
		return countries;
	}

	public static Country getCountryById(Integer id){
		for(Country country : allcountries){
			if(country.getId().equals(id)){
				return country;
			}
		}
		
		return null;
	}
	
	public static String getCountryByIMSI(Integer imsi){
		for(Country country : allcountries){
			if(country.getMcc().equals(imsi)){
				return country.getCountry();
			}
		}
		
		return null;
	}
	public static String getCountryByCountryName(String countryName){
		for(Country country : allcountries){
			if(country.equals(countryName)){
				return country.getName();
			}
		}
		
		return null;
	}

}
