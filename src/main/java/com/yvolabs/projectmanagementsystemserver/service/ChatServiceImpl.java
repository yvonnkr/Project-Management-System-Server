package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.model.Chat;
import com.yvolabs.projectmanagementsystemserver.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yvonne N
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
