package com.spellhaven.MVCforum.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spellhaven.MVCforum.dao.BDao;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(Model model) {
				
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bid = request.getParameter("bid");
		
		BDao dao = new BDao();
		dao.reply_view(bid);
		
		//model.addAttribute("content", dto); // 어? 이건 왜있냐??
	}

}
