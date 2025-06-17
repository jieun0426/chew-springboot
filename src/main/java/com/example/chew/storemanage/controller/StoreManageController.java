package com.example.chew.storemanage.controller;

import com.example.chew.dto.StoreDTO;
import com.example.chew.entity.StoreEntity;
import com.example.chew.entity.StoreImageEntity;
import com.example.chew.storemanage.service.StoreManageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class StoreManageController {
    @Autowired
    StoreManageService storeManageService;
    String path="C:\\MBC12AI\\spring_Boot\\chew\\src\\main\\resources\\static\\image";

    @RequestMapping(value="/storein")
    public String storein()
    {
        return"/storeManage/storeinput";
    }

    @RequestMapping(value="/storesave", method = RequestMethod.POST)
    public String storesave(MultipartHttpServletRequest mul, StoreDTO storeDTO) throws IllegalStateException, IOException
    {
        List<MultipartFile> files = mul.getFiles("storemainimage");

        StringBuilder fn=new StringBuilder();

        for (MultipartFile mf : files) {
            if(!mf.isEmpty()) {
                String fname = mf.getOriginalFilename();
                UUID uu = UUID.randomUUID();
                fname= uu.toString()+"_"+fname;
                mf.transferTo(new File(path+"\\"+fname));
                fn.append(fname).append(",");
            }
        }
        if (fn.length()>0) {
            fn.setLength(fn.length()-1);
        }
        storeDTO.setStoreimage(fn.toString());
        StoreEntity storeEntity=storeDTO.entity();
        storeManageService.insertstore(storeEntity);
        return "redirect:/sout";
    }

    @RequestMapping(value ="/sout")
    public String storeout(HttpServletRequest request, Model model, @RequestParam
            (required = false, defaultValue = "0", value = "page") int page)
    {
        Page<StoreEntity> listPage = storeManageService.list(page);
        int totalPage = listPage.getTotalPages();
        int nowpage = listPage.getPageable().getPageNumber()+1;//현재페이지//
        model.addAttribute("nowpage",nowpage);
        model.addAttribute("list",listPage.getContent());
        model.addAttribute("totalPage",totalPage);

        return "/storeManage/storeout";
    }

    @RequestMapping(value ="/sdelete")
    public String storedelete(HttpServletRequest request,Model m)
    {
        int storecode = Integer.parseInt(request.getParameter("storecode"));
        StoreEntity dto = storeManageService.selectOneStore(storecode);// 상세 이미지 1장 가져오기
        m.addAttribute("dto",dto);// 모델에 이미지 추가
        return "/storeManage/storedeleteview";
    }

    @RequestMapping(value ="/deleteOneStore",method = RequestMethod.POST)
    public String delete(HttpServletRequest request)
    {
        int storecode   = Integer.parseInt(request.getParameter("storecode"));
        String img 		= request.getParameter("storeimage");

        //1. 리뷰 삭제
        storeManageService.deleteReviewsByStorecode(storecode);
        //2. 예약 삭제
        storeManageService.deleteBookingByStorecode(storecode);
        //3. 좋아요 삭제
        storeManageService.deletelikesByStorecode(storecode);

        //4. 상세 이미지 파일 삭제 + DB 삭제
        List<StoreImageEntity> detailImages = storeManageService.detailImages(storecode);
        for (StoreImageEntity image : detailImages) {
            File detailImg = new File(path + File.separator + image.getId().getImageFilename());
            if (detailImg.exists()) detailImg.delete();
        }
        storeManageService.deleteDetailImages(storecode);

        // 5. 메인 이미지 파일 삭제
        File mainImg = new File(path + File.separator + img);
        if (mainImg.exists()) mainImg.delete();

        // 6. 가게 정보 삭제
        storeManageService.deleteStore(storecode);

        return "redirect:/sout";
    }

    @RequestMapping(value ="/smodify")
    public String ff(HttpServletRequest request,Model model)
    {
        int storecode=Integer.parseInt(request.getParameter("storecode"));
        StoreEntity dto=storeManageService.selectOneStore(storecode);
        List<StoreImageEntity> images=storeManageService.detailImages(storecode);
        model.addAttribute("dto",dto);
        model.addAttribute("images", images);

        return "/storeManage/storemodifyview";
    }

    @RequestMapping(value="/modify")
    public String gg(MultipartHttpServletRequest mul) throws IllegalStateException, IOException
    {
        int storecode   	 = Integer.parseInt(mul.getParameter("storecode"));
        String storename     = mul.getParameter("storename");
        String storeaddress  = mul.getParameter("storeaddress");
        String storecategory = mul.getParameter("storecategory");
        String storearea	 = mul.getParameter("storearea");
        String deleteImg=mul.getParameter("currentStoreImage");
        int parking = Integer.parseInt(mul.getParameter("parking"));
        float latitude=Float.parseFloat(mul.getParameter("latitude"));
        float longitude=Float.parseFloat(mul.getParameter("longitude"));
        String storehours=mul.getParameter("storehours");
        // 메인 이미지 처리
        MultipartFile mf = mul.getFile("storemainimage");

        if (mf.isEmpty()) {
            storeManageService.updatemodi1(storecode, storename, storeaddress, storecategory, storearea, parking, latitude, longitude, storehours);
        } else {
            String fname = mf.getOriginalFilename();
            UUID uu = UUID.randomUUID();
            fname = uu + "_" + fname;

            mf.transferTo(new File(path + File.separator + fname));
            storeManageService.updatemodi2(storecode, storename, storeaddress, storecategory, storearea, fname, parking, latitude, longitude, storehours);
            File ff=new File(path + File.separator + deleteImg);
            ff.delete();
        }

        // 상세 이미지 수정 처리 (최대 4개 이미지 중 수정된 부분만 처리)
        for (int i = 0; i < 4; i++) {
            MultipartFile detailFile = mul.getFile("storeimage" + i);
            if (detailFile != null && !detailFile.isEmpty()) {
                // 기존 이미지 filename을 폼에서 전달받음
                String oldFilename = mul.getParameter("oldFilename" + i);

                // 새 파일 이름 생성
                String newFilename = UUID.randomUUID() + "_" + detailFile.getOriginalFilename();
                detailFile.transferTo(new File(path + File.separator + newFilename));

                if (oldFilename != null && !oldFilename.isEmpty()) {
                    // 기존 이미지가 있으면 update
                    storeManageService.updateDetailImage(storecode, newFilename, oldFilename);
                    File ff = new File(path + File.separator + oldFilename);
                    ff.delete();
                } else {
                    // 기존 이미지가 없으면 insert
                    storeManageService.insertDetailImage(storecode, newFilename);
                }
            }
        }

        return "redirect:/sout";
    }

    @RequestMapping(value="/storemanage_detail")
    public String hh(HttpServletRequest request,Model m) {
        int num=Integer.parseInt(request.getParameter("num"));

        StoreEntity dto= storeManageService.selectOneStore(num);
        List<StoreImageEntity> images = storeManageService.detailImages(num);

        m.addAttribute("dto", dto);
        m.addAttribute("images", images);
        System.out.println("이미지:"+images);
        return "/storeManage/storemanage_detail";
    }

    @RequestMapping(value="/storemanage_search")
    public String hh2(HttpServletRequest request,Model model, @RequestParam
            (required = false, defaultValue = "0", value = "page") int page) {

        String search=request.getParameter("search");
        model.addAttribute("search", search);

        Page<StoreEntity> listPage = storeManageService.searchList(page,search);
        int totalPage = listPage.getTotalPages();
        int nowpage = listPage.getPageable().getPageNumber()+1;//현재페이지//
        model.addAttribute("nowpage",nowpage);
        model.addAttribute("list",listPage.getContent());
        model.addAttribute("totalPage",totalPage);
        return "/storeManage/storemanage_search";
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/deleteSelectedItems")
    public String deleteSelectedItems(@RequestParam("ids") String ids) {
        // 콤마로 구분된 문자열을 List<String>으로 변환
        List<String> idList = Arrays.asList(ids.split(","));

        // 각 id에 대해 삭제 작업 수행
        for (String id : idList) {
            try {
                int storecode = Integer.parseInt(id);
                StoreEntity sdto = storeManageService.selectOneStore(storecode);
                if (sdto == null) continue;
                String image = sdto.getStoreimage();

                //1. 리뷰 삭제
                storeManageService.deleteReviewsByStorecode(storecode);
                //2. 예약 삭제
                storeManageService.deleteBookingByStorecode(storecode);
                //3. 좋아요 삭제
                storeManageService.deletelikesByStorecode(storecode);

                //4. 상세 이미지 파일 삭제 + DB 삭제
                List<StoreImageEntity> detailImages = storeManageService.detailImages(storecode);
                for (StoreImageEntity image1 : detailImages) {
                    File detailImg = new File(path + File.separator + image1.getId().getImageFilename());
                    if (detailImg.exists()) detailImg.delete();
                }
                storeManageService.deleteDetailImages(storecode);

                // 5. 메인 이미지 파일 삭제
                File mainImg = new File(path + File.separator + image);
                if (mainImg.exists()) mainImg.delete();

                // 6. 가게 정보 삭제
                storeManageService.deleteStore(storecode);

            }catch (Exception e) {
                System.out.println("예외 발생: " + e.getMessage());
                e.printStackTrace(System.out);  // 확실하게 출력
                return "삭제 중 오류가 발생했습니다. 관리자에게 문의하세요.";
            }

        }

        return "ok";
    }
}
