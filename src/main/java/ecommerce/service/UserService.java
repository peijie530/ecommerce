package ecommerce.service;

import ecommerce.dto.RegisterRequest;
import ecommerce.entity.User;

public interface UserService {

	User register(RegisterRequest request);
}
