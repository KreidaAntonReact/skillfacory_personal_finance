package ru.personal.finance.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.Operation;
import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.dto.CategoryDto;
import ru.personal.finance.dto.OperationDto;
import ru.personal.finance.dto.UserDto;
import ru.personal.finance.dto.WalletDto;
import ru.personal.finance.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersistService {
    private final Path pathToFile = Path.of(System.getProperty("user.dir"), "data.json").toAbsolutePath().normalize();
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    public PersistService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void load() {
        if (!Files.exists(pathToFile)) {
            return;
        }

        try {
            UserDto[] usersDto = mapper.readValue(pathToFile.toFile(), UserDto[].class);
            List<User> users = new ArrayList<User>();

            for (UserDto userDto : usersDto) {
                users.add(toUser(userDto));
            }

            userRepository.replaceAll(users);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить данные из файла: " + e.getMessage(), e);
        }
    }

    public void save() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDto> usersDto = new ArrayList<UserDto>();

            for (User user : users) {
                usersDto.add(toUserDto(user));
            }

            mapper.writeValue(pathToFile.toFile(), usersDto);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить данные в файл: " + e.getMessage(), e);
        }
    }

    private User toUser(UserDto dto) {
        User user = new User();
        user.setLogin(dto.login);
        user.setPassword(dto.password);
        user.setWallet(toWallet(dto.wallet));
        return user;
    }

    private Wallet toWallet(WalletDto dto) {
        Wallet wallet = new Wallet();

        if (dto == null || dto.categories == null) {
            return wallet;
        }

        for (CategoryDto categoryDto : dto.categories) {
            wallet.addCategory(toCategory(categoryDto));
        }

        return wallet;
    }

    private Category toCategory(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name);
        category.setType(dto.type);

        if (dto.budget != null) {
            category.setBudget(dto.budget);
        }

        if (dto.operations != null) {
            for (OperationDto operationDto : dto.operations) {
                category.addOperation(toOperation(operationDto));
            }
        }

        return category;
    }

    private Operation toOperation(OperationDto dto) {
        Operation operation = new Operation();
        operation.setType(dto.type);
        operation.setAmount(dto.amount);
        return operation;
    }

    private UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.login = user.getLogin();
        dto.password = user.getPassword();
        dto.wallet = toWalletDto(user.getWallet());
        return dto;
    }

    private WalletDto toWalletDto(Wallet wallet) {
        WalletDto dto = new WalletDto();

        if (wallet == null) {
            return dto;
        }

        for (Category category : wallet.getCategories()) {
            dto.categories.add(toCategoryDto(category));
        }

        return dto;
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.name = category.getName();
        dto.type = category.getType();
        dto.budget = category.getBudget().orElse(null);

        for (Operation operation : category.getOperations()) {
            dto.operations.add(toOperationDto(operation));
        }

        return dto;
    }

    private OperationDto toOperationDto(Operation operation) {
        OperationDto dto = new OperationDto();
        dto.type = operation.getType();
        dto.amount = operation.getAmount();
        return dto;
    }
}
