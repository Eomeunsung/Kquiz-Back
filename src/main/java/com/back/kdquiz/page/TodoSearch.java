package com.back.kdquiz.page;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.page.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {
    Page<Quiz> search(PageRequestDTO pageRequestDTO);
}
