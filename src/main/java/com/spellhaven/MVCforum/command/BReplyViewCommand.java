package com.spellhaven.MVCforum.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spellhaven.MVCforum.dao.BDao;
import com.spellhaven.MVCforum.dto.BDto;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(Model model) {
				
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bid = request.getParameter("bid");
			
		BDao dao = new BDao();
		BDto dto = dao.reply_view(bid);
		
		model.addAttribute("reply_view", dto); // 어? 이건 왜있냐?? (reply view 열면 본문 내용이 맨 위에 뜨고, 그 밑에 댓글 폼 뜨게 구성할 거래. 선셍님이 그랬어.)
	}

}
