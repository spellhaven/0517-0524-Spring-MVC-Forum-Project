package com.spellhaven.MVCforum.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.spellhaven.MVCforum.dao.BDao;
import com.spellhaven.MVCforum.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(Model model) {

		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		
		model.addAttribute("list", dtos);
		// addAttribute는 ("이름", object) 이런 문법으로 쓰이는데,
		// Object 타입의 아무거나 실을 수 있다. 심지어 ArrayList도!
		
	}

}
