package com.web.Controller;

import com.web.Model.Customer;
import com.web.Service.CustomerService;
import com.web.Service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired private CustomerService service;

    @GetMapping("/users")
    public String showCustomersList(Model model){
        List<Customer> listCustomers = service.listAll();
        model.addAttribute("listCustomers", listCustomers);
        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model){
        model.addAttribute("customers", new Customer());
        model.addAttribute("pageTitle", "Add a New Customer");
        return "customer_form";
    }

    @PostMapping("/users/save")
    public String saveCustomer(Customer customer, RedirectAttributes ra){
        service.save(customer);
        ra.addFlashAttribute("message", "The customer was saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Customer customer = service.get(id);
            model.addAttribute("customers", customer);
            model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");
            ra.addFlashAttribute("message", "The customer with ID " + id + "has been updated");
            return "customer_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("users/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The customer with ID " + id + " has been deleted");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

}
