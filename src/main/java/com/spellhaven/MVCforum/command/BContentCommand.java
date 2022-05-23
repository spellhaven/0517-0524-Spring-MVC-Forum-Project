package com.spellhaven.MVCforum.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spellhaven.MVCforum.dao.BDao;
import com.spellhaven.MVCforum.dto.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bid = request.getParameter("bid");
				
		BDao dao = new BDao();
		BDto dto = dao.content_view(bid);
		
		model.addAttribute("content", dto); // content_view.jsp 안에서 쓴 EL과 이름을 맞췄다.
		
	}

}
