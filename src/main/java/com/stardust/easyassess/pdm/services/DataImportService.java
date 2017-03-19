package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.dao.repositories.HealthMinistryRepository;
import com.stardust.easyassess.pdm.dao.repositories.RoleRepository;
import com.stardust.easyassess.pdm.dao.repositories.UserRepository;
import com.stardust.easyassess.pdm.models.HealthMinistry;
import com.stardust.easyassess.pdm.models.Role;
import com.stardust.easyassess.pdm.models.User;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("importService")
@Scope("request")
public class DataImportService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HealthMinistryRepository ministryRepository;

    @Autowired
    RoleRepository roleRepository;

    public Map<Integer, Object> proceed(String path) {
        Map<Integer, Object> results = new HashMap();
        Workbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream(path);
            workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();
            for (int i = 0; i < rows; i++) {
                Cell province = sheet.getCell(1, i);
                Cell city = sheet.getCell(2, i);
                Cell district = sheet.getCell(3, i);
                Cell name = sheet.getCell(4, i);
                Cell role = sheet.getCell(5, i);
                Cell zipCode = sheet.getCell(6, i);
                Cell address = sheet.getCell(7, i);
                Cell contact = sheet.getCell(8, i);
                Cell phone = sheet.getCell(9, i);
                Cell mail = sheet.getCell(10, i);
                Cell username = sheet.getCell(11, i);
                Cell password = sheet.getCell(12, i);
                Cell supervisor = sheet.getCell(13, i);

                HealthMinistry ministry = new HealthMinistry();
                ministry.setName(name.getContents());
                ministry.setAddress(address.getContents());
                ministry.setCity(city.getContents());
                ministry.setDistrict(district.getContents());
                ministry.setProvince(province.getContents());
                ministry.setStatus("A");
                ministry.setZipcode(zipCode.getContents());
                ministry.setType("C");

                if (supervisor != null && supervisor.getContents() != null && !supervisor.getContents().isEmpty()) {
                    HealthMinistry superMinistry = ministryRepository.findBykey(supervisor.getContents());
                    if (superMinistry != null) {
                        superMinistry.getMinistries().add(ministry);
                        ministry.setSupervisor(superMinistry);
                    }
                }
                ministryRepository.save(ministry);

                User user = new User();


                List<Role> roleList = new ArrayList();
                List<HealthMinistry> ministryList = new ArrayList();
                ministryList.add(ministry);
                Role userRole = roleRepository.findBykey(role.getContents());
                roleList.add(userRole);

                user.setUsername(username.getContents());
                user.setStatus("A");
                user.setPassword(password.getContents());
                user.setRoles(roleList);
                user.setMinistries(ministryList);
                user.setCanLaunchAssessment(false);
                user.setName(contact.getContents().trim().isEmpty() ? "机构联系人" : contact.getContents());
                user.setPhone(phone.getContents());
                userRepository.save(user);
                results.put(i, user);



//                HealthMinistry ministry = ministryRepository.findBykey(name.getContents());
//
//                if (ministry != null && ministry.getId().compareTo(new Long(0)) > 0) {
//                    Cell account = sheet.getCell(3, i);
//                    Cell person = sheet.getCell(7, i);
//                    Cell phone = sheet.getCell(8, i);
//
//                    User user = new User();
//                    Role role = new Role();
//                    role.setId(new Long(2));
//                    role.setName("基层实验室");
//                    role.setStatus("A");
//
//                    List<Role> roleList = new ArrayList();
//                    List<HealthMinistry> ministryList = new ArrayList();
//                    ministryList.add(ministry);
//                    roleList.add(role);
//
//                    user.setUsername(account.getContents());
//                    user.setStatus("A");
//                    user.setPassword("user12345");
//                    user.setRoles(roleList);
//                    user.setMinistries(ministryList);
//                    user.setCanLaunchAssessment(false);
//                    user.setName(person.getContents().trim().isEmpty() ? "机构联系人" : person.getContents());
//                    user.setPhone(phone.getContents());
//                    userRepository.save(user);
//                    results.put(i, user.getUsername() + ":" + name.getContents());
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

        return results;
    }
}

