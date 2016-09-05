package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.dao.repositories.HealthMinistryRepository;
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

    public Map<Integer, Object> proceed(String path) {
        Map<Integer, Object> results = new HashMap();
        Workbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream(path);
            workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            int columns = sheet.getColumns();
            int rows = sheet.getRows();
            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < columns; j++) {
//                    Cell cell = sheet.getCell(j, i);
//                    System.out.print(cell.getContents() + " ");
//                }
//                System.out.println();
                Cell name = sheet.getCell(2, i);

                HealthMinistry ministry = ministryRepository.findBykey(name.getContents());

                if (ministry != null && ministry.getId().compareTo(new Long(0)) > 0) {
                    Cell account = sheet.getCell(3, i);
                    Cell person = sheet.getCell(7, i);
                    Cell phone = sheet.getCell(8, i);

                    User user = new User();
                    Role role = new Role();
                    role.setId(new Long(2));
                    role.setName("基层实验室");
                    role.setStatus("A");

                    List<Role> roleList = new ArrayList();
                    List<HealthMinistry> ministryList = new ArrayList();
                    ministryList.add(ministry);
                    roleList.add(role);

                    user.setUsername(account.getContents());
                    user.setStatus("A");
                    user.setPassword("user12345");
                    user.setRoles(roleList);
                    user.setMinistries(ministryList);
                    user.setCanLaunchAssessment(false);
                    user.setName(person.getContents().trim().isEmpty() ? "机构联系人" : person.getContents());
                    user.setPhone(phone.getContents());
                    userRepository.save(user);
                    results.put(i, user.getUsername() + ":" + name.getContents());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

        return results;
    }
}

