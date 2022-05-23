package com.spellhaven.MVCforum.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spellhaven.MVCforum.dao.BDao;

public class BDeleteCommand implements BCommand {

	@Override
	public void execute(Model model) {
		
		// 내가 직접 생각해서 만들어 보는! BDeleteCommand!! 😀
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bid = request.getParameter("bid");
		
		BDao dao = new BDao();
		dao.delete(bid);
		
	}

}
