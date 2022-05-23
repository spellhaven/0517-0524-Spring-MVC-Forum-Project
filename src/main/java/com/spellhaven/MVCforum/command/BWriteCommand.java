package com.spellhaven.MVCforum.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spellhaven.MVCforum.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	public void execute(Model model) {
		
		// 아까 BController에서 model에 실어 보내 준 request 있지. 걔는 여기서 바로 .getParameter 할 수 없다 킹받게. 😩
		// 그래서 이렇게 Map으로 빼야 하는데, 구문이 어려우니까 그냥 그러려니 하셈. 어차피 여기서 한 번밖에 안 씀 ㅋ 나중에 더 좋은 방법 배움😀
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
				
		BDao dao = new BDao();
		dao.write(bname, btitle, bcontent);
	}

}
