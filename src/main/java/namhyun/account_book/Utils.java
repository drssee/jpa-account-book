package namhyun.account_book;

import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SearchCondition;

public class Utils {

    public static SearchCondition getSearchCondition(String year, String month, MemberDto memberDto) {
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setYear(year);
        searchCondition.setMonth(month);
        searchCondition.setMemberDto(memberDto);
        return searchCondition;
    }
}
