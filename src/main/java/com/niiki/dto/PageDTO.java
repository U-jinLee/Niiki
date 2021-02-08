package com.niiki.dto;

public class PageDTO {
	//1, 11, 21, 31..
	private int startPage;
	//10, 20, 30, 40..
	private int endPage;
	private boolean prev;
	private boolean next;
	//게시판의 전체 건수
	private int total;
	private Criteria cri;
	private MCriteria mcri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		//System.out.println("cri.getAmount="+cri.getAmount());
		/*endPage*/
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
		/*startPage*/
		this.startPage = this.endPage-9;
		int realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount()));
		if(realEnd < endPage) {
			this.endPage = realEnd;
		}
		//prev
		this.prev = this.startPage > 1;
		//next
		this.next = this.endPage < realEnd; 
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		System.out.println("PageDTO="+cri);
		this.cri = cri;
	}

	public MCriteria getMcri() {
		return mcri;
	}

	public void setMcri(MCriteria mcri) {
		this.mcri = mcri;
	}

	@Override
	public String toString() {
		return "PageDTO [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
				+ ", total=" + total + ", cri=" + cri + ", mcri=" + mcri + "]";
	}


	
}
