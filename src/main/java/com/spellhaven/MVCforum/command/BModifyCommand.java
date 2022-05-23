package com.spellhaven.MVCforum.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spellhaven.MVCforum.dao.BDao;

public class BModifyCommand implements BCommand {

	@Override
	public void execute(Model model) {

		// ㅈㅅ... "여기 좀 전부터 졸려서 그냥 무지성으로 베끼기만 함, 무슨 소린지 1도 모름" ㅋ.
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		String bid = request.getParameter("bid");
		
		BDao dao = new BDao();
		dao.modify(bid, bname, btitle, bcontent);
		
	}

}
