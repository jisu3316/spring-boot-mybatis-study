package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalCount) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int tempEndPage = (int) (Math.ceil(totalCount / (double) 10));

        int endNumber = Math.min(startNumber + BAR_LENGTH, tempEndPage);

        return IntStream.range(startNumber, endNumber).boxed().collect(toList());
    }

    public Integer getEndNumber(int currentPageNumber, int totalCount) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int tempEndPage = (int) (Math.ceil(totalCount / (double) 10));
        return Math.min(startNumber + BAR_LENGTH, tempEndPage);
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
