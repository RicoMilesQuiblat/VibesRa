package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Admin;
import com.NeuralN.VibesRa.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(int adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        return admin.orElse(null);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdmin(int adminId) {
        adminRepository.deleteById(adminId);
    }

    public Admin updateAdmin(int adminId, Admin updatedAdmin) {
        Admin existingAdmin = getAdminById(adminId);
        if (existingAdmin == null) {
            return null; // or throw exception
        }
        updatedAdmin.setAdminId(adminId);
        return adminRepository.save(updatedAdmin);
    }
}
