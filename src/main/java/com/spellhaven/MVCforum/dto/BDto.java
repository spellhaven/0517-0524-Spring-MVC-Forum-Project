package com.spellhaven.MVCforum.dto;

import java.sql.Timestamp;

public class BDto {
	
	// 이 안의 변수들은 오라클 SQL 변수들과 이름이 똑같아야 한다. 주의하디, 크킄,
	
	int bid; // 게시글 번호(기본키)
	String bname; // 글쓴이
	String btitle; // 글 제 목(Post My Neck)
	Timestamp bdate; // 게시글 작성 날짜/시간 (java.sql 패키지의 Timestamp여야 해. java.security 아니고.)
	int bhit; // 조회수
	int bgroup; // 댓글 그룹
	int bstep; // 댓글 스텝 (몇 단계의 대댓글인가?)
	int bindent; // 댓글 들여쓰기 단계
	
	
	public BDto() {
		super();
	}

	public BDto(int bid, String bname, String btitle, Timestamp bdate, int bhit, int bgroup, int bstep, int bindent) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.btitle = btitle;
		this.bdate = bdate;
		this.bhit = bhit;
		this.bgroup = bgroup;
		this.bstep = bstep;
		this.bindent = bindent;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBtitle() {
		return btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public Timestamp getBdate() {
		return bdate;
	}

	public void setBdate(Timestamp bdate) {
		this.bdate = bdate;
	}

	public int getBhit() {
		return bhit;
	}

	public void setBhit(int bhit) {
		this.bhit = bhit;
	}

	public int getBgroup() {
		return bgroup;
	}

	public void setBgroup(int bgroup) {
		this.bgroup = bgroup;
	}

	public int getBstep() {
		return bstep;
	}

	public void setBstep(int bstep) {
		this.bstep = bstep;
	}

	public int getBindent() {
		return bindent;
	}

	public void setBindent(int bindent) {
		this.bindent = bindent;
	}
	
}
