package com.example.hotel_mvc.controller;

import com.example.hotel_mvc.model.entity.Hotel;
import com.example.hotel_mvc.model.entity.Systemmanager;
import com.example.hotel_mvc.model.repository.database.HotelRepository;
import com.example.hotel_mvc.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    Date create;
    int ID;

    @GetMapping("/hotel")
    public String showHotelPage(Model model) {
        model.addAttribute("lsthotel", hotelRepository.findAll());
        return "layout/hotel";
    }

    @PostMapping("/updatehotel")
    public String updateHotel(Model model,
                            @RequestParam(value = "id", required = false) int id,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "namefurigana", required = false) String name_furigana,
                            @RequestParam(value = "url", required = false) String url,
                            @RequestParam(value = "tradename", required = false) String trade_name,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "phonenumber", required = false) String phone_number,
                            @RequestParam(value = "faxnumber", required = false) String fax_number,
                            @RequestParam(value = "prefcode", required = false) String pref_code,
                            @RequestParam(value = "postalcode", required = false) String postal_code,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "buildingname", required = false) String building_name,
                            @RequestParam(value = "licensenumber", required = false) String license_number,
                            @RequestParam(value = "image", required = false) String image,
                            @RequestParam(value = "paymentmethod", required = false) String payment_method,
                            @RequestParam(value = "updateat",required = false) Date updateat
    ) {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName(name);
        hotel.setNamefurigana(name_furigana);
        hotel.setUrl(url);
        hotel.setTradename(trade_name);
        hotel.setType(type);
        hotel.setPhonenumber(phone_number);
        hotel.setFaxnumber(fax_number);
        hotel.setPrefcode(pref_code);
        hotel.setPostalcode(postal_code);
        hotel.setAddress(address);
        hotel.setBuildingname(building_name);
        hotel.setLicensenumber(license_number);
        hotel.setImage(image);
        hotel.setPaymentmethod(payment_method);
        hotel.setCreateat(create);
        hotel.setUpdateat(updateat);
        hotelRepository.save(hotel);
        return "redirect:/hotel";
    }

    @PostMapping("/savehotel")
    public String saveHotel(Model model,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "namefurigana", required = false) String name_furigana,
                            @RequestParam(value = "url", required = false) String url,
                            @RequestParam(value = "tradename", required = false) String trade_name,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "phonenumber", required = false) String phone_number,
                            @RequestParam(value = "faxnumber", required = false) String fax_number,
                            @RequestParam(value = "prefcode", required = false) String pref_code,
                            @RequestParam(value = "postalcode", required = false) String postal_code,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "buildingname", required = false) String building_name,
                            @RequestParam(value = "licensenumber", required = false) String license_number,
                            @RequestParam(value = "image", required = false) String image,
                            @RequestParam(value = "paymentmethod", required = false) String payment_method
    ) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setNamefurigana(name_furigana);
        hotel.setUrl(url);
        hotel.setTradename(trade_name);
        hotel.setType(type);
        hotel.setPhonenumber(phone_number);
        hotel.setFaxnumber(fax_number);
        hotel.setPrefcode(pref_code);
        hotel.setPostalcode(postal_code);
        hotel.setAddress(address);
        hotel.setBuildingname(building_name);
        hotel.setLicensenumber(license_number);
        hotel.setImage(image);
        hotel.setPaymentmethod(payment_method);
        hotel.setCreateat(new Date());
        hotelRepository.save(hotel);
        return "redirect:/hotel";
    }

    @GetMapping("/hotel/edit/{id}")
    @ResponseBody
    public Hotel showEditHotel(
            Model model,
            @PathVariable(name = "id") int id) {
        Hotel datahotel = hotelService.getById(id);
        create = datahotel.getCreateat();
        System.out.println("time create: " + create );
        model.addAttribute("datahotelpreview", datahotel);
//        return "layout/update";
        return hotelRepository.getById(id);
    }

    @GetMapping("/hotel/preview/{id}")
    public String showHotelPreview(Model model, @PathVariable(name = "id") int id){
        Hotel datahotel = hotelService.getById(id);
        model.addAttribute("datahotelpreview", datahotel);
        return "layout/preview";
    }
//    @ResponseBody
//    public Hotel showPreviewHotel(
//            Model model,
//            @PathVariable(name = "id") int id) {
//        Hotel datahotel = hotelService.getById(id);
//        model.addAttribute("datahotelpreview", datahotel);
////        return "layout/update";
//        return hotelRepository.getById(id);
//    }

    @RequestMapping("/hotel/delete/{id}")
    public String deleteHotel(@PathVariable(name = "id") int id){
        hotelService.delete(id);
        return "redirect:/hotel";
    }
}
