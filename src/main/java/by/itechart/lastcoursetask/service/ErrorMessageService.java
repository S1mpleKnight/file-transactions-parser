package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.ErrorMessageDto;
import by.itechart.lastcoursetask.entity.ErrorMessage;
import by.itechart.lastcoursetask.exception.ErrorMessageNotFoundException;
import by.itechart.lastcoursetask.repository.ErrorMessageRepository;
import by.itechart.lastcoursetask.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorMessageService {
    private final ErrorMessageRepository messageRepository;
    private final OperatorService operatorService;
    private final EntityMapper mapper;

    public Page<ErrorMessageDto> findAll(Pageable pageable) {
        return getMessagesPage(messageRepository.findAll(pageable));
    }

    public ErrorMessageDto findMessageById(Long id) {
        if (messageRepository.existsById(id)) {
            return mapper.mapToErrorMessageDto(messageRepository.getById(id));
        }
        throw new ErrorMessageNotFoundException("Error message not found, id: " + id);
    }

    public List<ErrorMessageDto> findMessagesByOperatorId(Long id) {
        operatorService.findById(id);
        return getErrorMessageDto(messageRepository.findByOperator_Id(id));
    }

    public void saveAll(List<ErrorMessage> messages) {
        for (ErrorMessage message : messages) {
            save(message);
        }
    }

    private Page<ErrorMessageDto> getMessagesPage(Page<ErrorMessage> messages) {
        return new PageImpl<>(getErrorMessageDto(messages.stream().toList()));
    }

    private List<ErrorMessageDto> getErrorMessageDto(List<ErrorMessage> messages) {
        return messages.stream().map(mapper::mapToErrorMessageDto).collect(Collectors.toList());
    }

    private void save(ErrorMessage message) {
        messageRepository.save(message);
    }
}
