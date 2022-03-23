package com.example.hotelspringmvc.controller;

import com.example.hotelspringmvc.model.entity.Hotel;
import com.example.hotelspringmvc.model.repository.HotelRepository;
import com.example.hotelspringmvc.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showHotelPage(ModelMap model){
        model.put("listhotel",hotelRepository.findAll());
        return "layout/hotel";
    }
    @PostMapping("/updatehotel")
    public String updateHotel(ModelMap model,
                              @RequestParam(value = "id",required = false)int id,
                              @RequestParam(value="name",required = false)String name,
                              @RequestParam(value="namefurigana",required = false)String name_furigana,
                              @RequestParam(value = "url",required = false)String url,
                              @RequestParam(value = "tradename",required = false)String trade_name,
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
                              @RequestParam(value = "updateat",required = false) Date updateat){
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
}
