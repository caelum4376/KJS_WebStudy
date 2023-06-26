package com.sist.manager;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.*;

public class DataCollectionManager {
	
	public static void main(String[] args) {
		List<hotelVO> hList = new ArrayList<hotelVO>(); // hotelVo 클래스 데이터형 list 생성
		List<roomVO> rList = new ArrayList<roomVO>(); // roomVO 클래스 데이터형 list 생성
		hotelDAO dao = hotelDAO.newInstance();
		
		try
		{
			// 숙소 리스트페이지
			String listURL = "http://www.jejutori.com/CS/Goods/Lodge/?page="; 
			
			// 숙소 상세페이지
			String DetailURL = "http://www.jejutori.com";
			
			hotelCategoryVO hcVo = new hotelCategoryVO();
			hotelVO hVo = new hotelVO();
			roomVO rVo = new roomVO();
			
			
			for(int i=0; i<=31; i++)
			{
				Document doc = Jsoup.connect(listURL + i).get();
				Elements hcTitle = doc.select("table#ctl00_CPMiddle_LodgeList1_grdList table.id_tdlodegcont table tr td div");
				Elements link = doc.select("table#ctl00_CPMiddle_LodgeList1_grdList table.id_tdlodegcont a:nth-child(n+3)");
				for (int j=0; j<hcTitle.size(); j++) {
					
					// 카테고리정보
					// 카테고리 번호, 타이틀명
					String hcT = hcTitle.get(j).text().substring(hcTitle.get(j).text().indexOf("/")+2);
					if (hcT.equals("펜션")) {
						hVo.setHcno(1);
					} else if (hcT.equals("리조트,콘도")) {
						hVo.setHcno(2);
					} else if (hcT.equals("호텔")) {
						hVo.setHcno(3);
					} else if (hcT.equals("기타")) {
						hVo.setHcno(4);
					}
//					System.out.println(hcVo.getHcno());
//					System.out.println(hcVo.getHcTitle());
					
					// 상세정보
					// 링크
					String detailLink = DetailURL + link.get(j).attr("href");
					System.out.println(detailLink);
	
					Document detailDoc = Jsoup.connect(detailLink).get();
					Elements hAddr = detailDoc.select("span#ctl00_CPMiddle_LodgeDetail1_lblTitleRegion");
					Elements hTitle = detailDoc.select("span#ctl00_CPMiddle_LodgeDetail1_lblTitleName");
					Elements hPoster = detailDoc.select("table#ctl00_CPMiddle_LodgeDetail1_ImageGallery1_tblGallery img");
					Elements hEtc = detailDoc.select("span#ctl00_CPMiddle_LodgeDetail1_tFacility");
					Elements hContent = detailDoc.select("table tr td table tr td table tr td table tr td table tr td p");
					Elements rTitle = detailDoc.select("table tr td table tr td div table tr td table tr td h1");
					Elements rTitle2 = detailDoc.select("table tr td table tr td div table tr td table tr td h1 span");
					Elements person = detailDoc.select("table.tblPackageItems tr:nth-child(5n+2)");
					Elements structure = detailDoc.select("table.tblPackageItems tr:nth-child(5n+3)");
					Elements special = detailDoc.select("table.tblPackageItems tr:nth-child(5n+4)");
					Elements price = detailDoc.select("table.tblPackageItems tr:nth-child(5n+5)");
					
					// 상세정보 - 숙소명
					hVo.setName(hTitle.get(0).text());
//					System.out.println(hVo.getName());
					
					// 상세정보 - 주소
					hVo.setAddr(hAddr.get(0).text());
//					System.out.println(hVo.getAddr());
					
					// 상세정보 - 이미지
					StringBuilder poster = new StringBuilder();
					for (int k=0; k<hPoster.size(); k++) {
						poster.append(hPoster.get(k).attr("src"));
						if (k < hPoster.size()-1) {
							poster.append("^");
						}
					}
					hVo.setPoster(poster.toString());
//					System.out.println(hVo.getPoster());
					
					// 상세정보 - 부대시설
					hVo.setEtc(hEtc.get(0).text());
//					System.out.println(hVo.getEtc());
					
					// 상세보기 - 숙소 정보
					StringBuilder content = new StringBuilder();
					for (int k=0; k<hContent.size(); k++) {
						content.append(hContent.get(k).text());
						if (k < hContent.size()-1) {
							content.append("^");
						}
					}
					hVo.setContent(content.toString());
//					System.out.println(hVo.getContent());
					
					// 상세보기 - 숙소 고유번호
					hVo.setHuno(Integer.parseInt(detailLink.substring(detailLink.lastIndexOf("=")+1)));
//					System.out.println(hVo.getHuno());
					
					// 데이터 저장
					dao.hotelInsert(hVo);
					
					// 방정보
					for (int k=0; k<rTitle.size(); k++) {
						// 방이름
						rVo.setRname(rTitle.get(k).text().substring
								(0, rTitle.get(k).text().lastIndexOf(rTitle2.get(k).text())-1));
//						System.out.println(rVo.getRname());
						
						// 방정원
						rVo.setPerson(person.get(k).text().substring
								(person.get(k).text().indexOf("최대인원")+5));
//						System.out.println(rVo.getPerson());
						
						// 객실구조
						rVo.setSturcture(structure.get(k).text().substring
								(structure.get(k).text().indexOf("객실구조")+5));
//						System.out.println(rVo.getSturcture());
						
						// 특이사항
						rVo.setSpecial(special.get(k).text());
//						System.out.println(rVo.getSpecial());
						
						// 빈방재고
						if (hcT.equals("호텔")) {
							rVo.setAccount(5);
						} else {
							rVo.setAccount(1);
						}
//						System.out.println(rVo.getAccount());
						
						// 방가격
						rVo.setPrice(Integer.parseInt(price.get(k).text().substring
								(price.get(k).text().indexOf("주중")+3, price.get(k).text().indexOf("원"))
								.replace(",", "")));
//						System.out.println(rVo.getPrice());
						
						// 방사진
						Elements rPoster = detailDoc.select("table#ctl00_CPMiddle_LodgeDetail1_RoomDetail1_gList_ctl"+ String.format("%02d", (k*2)) +"_glyRoom_tblGallery tr td img");
						StringBuilder poster2 = new StringBuilder();
						for (int l=0; l<rPoster.size(); l++) {
							poster2.append(rPoster.get(l).attr("src"));
							if (l < rPoster.size()-1) {
								poster2.append("^");
							}
						}
						rVo.setRposter(poster2.toString());
//						System.out.println(rVo.getRposter());
						
						// 숙소번호
						rVo.setHdno(hVo.getHuno());
//						System.out.println(rVo.getHdno());
						
						// 데이터 저장
						dao.roomInsert(rVo);
					}
					
//					hcList.add(hcVo);
//					hList.add(hVo);
//					rList.add(rVo);
				}				
			}
			System.out.println("숙소 갯수" + hList.size());
			System.out.println("방 갯수" + rList.size());
				
			
			
//			System.out.println("저장 완료!!");
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			
		}
		
	}

}