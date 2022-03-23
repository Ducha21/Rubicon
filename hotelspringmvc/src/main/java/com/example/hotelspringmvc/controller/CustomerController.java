package com.example.hotelspringmvc.controller;

import com.example.hotelspringmvc.model.entity.Customer;
import com.example.hotelspringmvc.model.repository.CompanyRepository;
import com.example.hotelspringmvc.model.repository.CustomerRepository;
import com.example.hotelspringmvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;
    Date create;

    @GetMapping("/customer")
    public String showCustomerPage(ModelMap model){
        model.put("lstcustomers", customerRepository.findAll());
        return "layout/customer";
    }

    @PostMapping("/savecustomer")
    public String saveCustomer(Model model,
                               @RequestParam(value = "first_name", required = false) String first_name,
                               @RequestParam(value = "first_name_furigana", required = false) String first_name_furigana,
                               @RequestParam(value = "last_name", required = false) String last_name,
                               @RequestParam(value = "last_name_furigana", required = false) String last_name_furigana,
                               @RequestParam(value = "postal_code", required = false) String postal_code,
                               @RequestParam(value = "country", required = false) String country,
                               @RequestParam(value = "pref_code", required = false) String pref_code,
                               @RequestParam(value = "address", required = false) String address,
                               @RequestParam(value = "building_name", required = false) String building_name,
                               @RequestParam(value = "work_place", required = false) String work_place,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "phone_number", required = false) String phone_number,
                               @RequestParam(value = "birthday", required = false) String birthday,
                               @RequestParam(value = "gender", required = false) String gender,
                               @RequestParam(value = "family_phone", required = false) String family_phone
    ){
        Customer customer = new Customer();
        customer.setFirstName(first_name);
        customer.setFirstNameFurigana(first_name_furigana);
        customer.setLastName(last_name);
        customer.setLastNameFurigana(last_name_furigana);
        customer.setPostalCode(postal_code);
        customer.setCountry(country);
        customer.setPrefCode(pref_code);
        customer.setAddress(address);
        customer.setBuildingName(building_name);
        customer.setWorkPlace(work_place);
        customer.setEmail(email);
        customer.setPhoneNumber(phone_number);
        customer.setBirthday(birthday);
        customer.setGender(gender);
        customer.setFamilyPhone(family_phone);
        customer.setCreateAt(new Date());
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/customer/edit/{id}")
    @ResponseBody
    public Customer showEditHotel(
            Model model,
            @PathVariable(name = "id") int id) {
        Customer datacustomer = customerService.getById(id);
        create = datacustomer.getCreateAt();
        System.out.println("time create: " + create );
        model.addAttribute("datahotelpreview", datacustomer);
//        return "layout/update";
        return customerRepository.getById(id);
    }

    @PostMapping("/updatecustomer")
    public String updateCustomer(Model model,
                                 @RequestParam(value = "id", required = false) int id,
                                 @RequestParam(value = "first_name", required = false) String first_name,
                                 @RequestParam(value = "first_name_furigana", required = false) String first_name_furigana,
                                 @RequestParam(value = "last_name", required = false) String last_name,
                                 @RequestParam(value = "last_name_furigana", required = false) String last_name_furigana,
                                 @RequestParam(value = "postal_code", required = false) String postal_code,
                                 @RequestParam(value = "country", required = false) String country,
                                 @RequestParam(value = "pref_code", required = false) String pref_code,
                                 @RequestParam(value = "address", required = false) String address,
                                 @RequestParam(value = "building_name", required = false) String building_name,
                                 @RequestParam(value = "work_place", required = false) String work_place,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "phone_number", required = false) String phone_number,
                                 @RequestParam(value = "birthday", required = false) String birthday,
                                 @RequestParam(value = "gender", required = false) String gender,
                                 @RequestParam(value = "family_phone", required = false) String family_phone,
                                 @RequestParam(value = "update_at", required = false) Date update_at
    ){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(first_name);
        customer.setFirstNameFurigana(first_name_furigana);
        customer.setLastName(last_name);
        customer.setLastNameFurigana(last_name_furigana);
        customer.setPostalCode(postal_code);
        customer.setCountry(country);
        customer.setPrefCode(pref_code);
        customer.setAddress(address);
        customer.setBuildingName(building_name);
        customer.setWorkPlace(work_place);
        customer.setEmail(email);
        customer.setPhoneNumber(phone_number);
        customer.setBirthday(birthday);
        customer.setGender(gender);
        customer.setFamilyPhone(family_phone);
        customer.setCreateAt(create);
        customer.setUpdateAt(update_at);
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @RequestMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id")int id){
        customerService.delete(id);
        return "redirect:/customer";
    }
}
