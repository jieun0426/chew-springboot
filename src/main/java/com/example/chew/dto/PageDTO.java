package com.example.chew.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageDTO {
    //현재 페이지, 시작 페이지, 끝 페이지, 전체 레코드수, 한 페이지에 나타낼 레코드수, 전체 페이지, 쿼리에 쓰일 start,end
    private int nowPage, startPage, endPage, total, cntPerPage, lastPage, start, end;
    private int cntPage=5; //한번에 나타낼 페이지 수
    public int getCntPage() { return cntPage; }
    public void setCntPage(int cntPage) {
        this.cntPage=cntPage;
    }

    public PageDTO(int total, int nowPage, int cntPerPage) {
        setNowPage(nowPage);
        setCntPerPage(cntPerPage);
        setTotal(total);
        calcLastPage(getTotal(), getCntPerPage());
        calcStartEndPage(getNowPage(), cntPage);
        calcStartEnd(getNowPage(),getCntPerPage());
    }

    //전체 페이지 수
    private void calcLastPage(int total, int cntPerPage) {
        setLastPage((int)Math.ceil((double)total/(double)cntPerPage));
    }

    //시작페이지와 끝페이지
    private void calcStartEndPage(int nowPage, int cntPage) {
        setEndPage(((int)Math.ceil((double)nowPage/(double)cntPage))*cntPage);
        setStartPage(getEndPage()-cntPage+1);

        if(getLastPage()<getEndPage()) {
            setEndPage(getLastPage());
        }

        if(getStartPage()<1) {
            setStartPage(1);
        }
    }

    //DB쿼리에 정의할 start,end
    private void calcStartEnd(int nowPage, int cntPerPage) {
        setEnd(nowPage*cntPerPage);
        setStart(getEnd()-cntPerPage+1);
    }
}
