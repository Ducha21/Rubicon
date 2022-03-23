package com.example.hotel_mvc.controller;

import com.example.hotel_mvc.model.entity.Company;
import com.example.hotel_mvc.model.repository.database.CompanyRepository;
import com.example.hotel_mvc.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    Date create;

    @GetMapping("/company")
    public String showCompany(Model model){
        model.addAttribute("lstcompany", companyRepository.findAll());
        return "layout/company";
    }

    @PostMapping("/savecompany")
    public String saveCompany(Model model,
                              @RequestParam(value = "name",required = false) String name,
                              @RequestParam(value = "name_furigana",required = false) String name_furigana,
                              @RequestParam(value = "phone_number",required = false) String phone_number,
                              @RequestParam(value = "email",required = false) String email,
                              @RequestParam(value = "pref_code", required = false) String pref_code,
                              @RequestParam(value = "address",required = false) String address,
                              @RequestParam(value = "postal_code",required = false) String postal_code,
                              @RequestParam(value = "building_name",required = false) String building_name
                              ){
        Company company = new Company();
        company.setName(name);
        company.setNameFurigana(name_furigana);
        company.setPhoneNumber(phone_number);
        company.setEmail(email);
        company.setPrefCode(pref_code);
        company.setAddress(address);
        company.setPostalCode(postal_code);
        company.setBuildingName(building_name);
        company.setCreateAt(new Date());
        companyRepository.save(company);
        return "redirect:/company";
    }

    @RequestMapping("/company/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) {
        companyService.delete(id);
        return "redirect:/company";
    }

    @GetMapping("/company/edit/{id}")
    @ResponseBody
    public Company editCompanyPage(Model model,
                                  @PathVariable(name = "id") int id){
        Company company = companyService.getById(id);
        create = company.getCreateAt();
        model.addAttribute("datacompany", company);
        return companyRepository.getById(id);
    }

    @PostMapping("/editcompany")
    public String updateCompany(Model model,
                                @RequestParam(value = "id",required = false) int id,
                                @RequestParam(value = "name",required = false) String name,
                                @RequestParam(value = "name_furigana",required = false) String name_furigana,
                                @RequestParam(value = "phone_number",required = false) String phone_number,
                                @RequestParam(value = "email",required = false) String email,
                                @RequestParam(value = "pref_code", required = false) String pref_code,
                                @RequestParam(value = "address",required = false) String address,
                                @RequestParam(value = "postal_code",required = false) String postal_code,
                                @RequestParam(value = "building_name",required = false) String building_name
                                ){

        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setNameFurigana(name_furigana);
        company.setPhoneNumber(phone_number);
        company.setEmail(email);
        company.setPrefCode(pref_code);
        company.setAddress(address);
        company.setPostalCode(postal_code);
        company.setBuildingName(building_name);
        company.setCreateAt(create);
        company.setUpdateAT(new Date());
        companyRepository.save(company);
        return "redirect:/company";
    }
}
