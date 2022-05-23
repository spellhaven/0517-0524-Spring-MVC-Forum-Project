package com.spellhaven.MVCforum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spellhaven.MVCforum.command.BCommand;
import com.spellhaven.MVCforum.command.BListCommand;
import com.spellhaven.MVCforum.command.BWriteCommand;

@Controller
public class BController {
	
	BCommand command = null;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		command = new BListCommand();
//		BListCommand command = new BListCommand(); 라고 쓸 수도 있지만, 코드 양을 줄이기 위해서
// 		L14에 쓰인 걸 봐라. 걔랑 연계해서 Bcommand로 만든 객체를 가져왔다. 업캐스팅. 이래도 된다.		
		command.execute(model);
		
		return "list";
	}
	
	@RequestMapping("/write")
	public String write(Model model) {
		command = new BWriteCommand();
		command.execute(model);
		
		return "write";
	}
	
}









