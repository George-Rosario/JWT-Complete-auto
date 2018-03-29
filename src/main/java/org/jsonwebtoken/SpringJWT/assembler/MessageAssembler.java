package org.jsonwebtoken.SpringJWT.assembler;

import java.util.ArrayList;
import java.util.List;

import org.jsonwebtoken.SpringJWT.dto.MessagesDTO;
import org.jsonwebtoken.SpringJWT.model.Messages;
import org.springframework.stereotype.Component;

@Component
public class MessageAssembler {
	
	public ArrayList<MessagesDTO> messageDaoToMessageDto(List<Messages> msg)
	{
		for (Messages messages : msg) {
			System.out.println(messages.getTextMessage());
		}
		ArrayList<MessagesDTO> msgDTOlist = new ArrayList<>();
		for (Messages messages : msg) {
			MessagesDTO messagesDTO = new MessagesDTO();
			messagesDTO.setMesageId((messages.getMessageId())!=null ? messages.getMessageId() : 0);
			messagesDTO.setSentFrom((messages.getFromUser().getEmail())!=null ? messages.getFromUser().getEmail() : " ");
			messagesDTO.setTextMessage((messages.getTextMessage())!=null ? messages.getTextMessage() : " ");
			
			msgDTOlist.add(messagesDTO);
		
		}
		
		return msgDTOlist;
		
	}

}
