package com.mobileapi.mobileapi.ui;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobileapi.mobileapi.exceptions.UserServiceException;
import com.mobileapi.mobileapi.service.AddressService;
import com.mobileapi.mobileapi.service.UserService;
import com.mobileapi.mobileapi.shared.dto.AddressDto;
import com.mobileapi.mobileapi.shared.dto.UserDto;
import com.mobileapi.mobileapi.ui.model.request.UserDetailsRequestModel;
import com.mobileapi.mobileapi.ui.model.response.AddressRest;
import com.mobileapi.mobileapi.ui.model.response.ErrorMessages;
import com.mobileapi.mobileapi.ui.model.response.OperationStatusModel;
import com.mobileapi.mobileapi.ui.model.response.RequestOperationName;
import com.mobileapi.mobileapi.ui.model.response.RequestOperationStatus;
import com.mobileapi.mobileapi.ui.model.response.UserRest;

import org.apache.catalina.mbeans.UserMBean;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userFound = userService.getUserByUserId(id);
		if (userFound == null)
			throw new UsernameNotFoundException(id);

		BeanUtils.copyProperties(userFound, returnValue);

		return returnValue;
	}

	@PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		returnValue = modelMapper.map(createdUser, UserRest.class);
		return returnValue;
	}

	@PutMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@GetMapping(path = "/")
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit) {
		List<UserRest> returnValue = new ArrayList<>();

		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}

		return returnValue;
	}

	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<AddressRest> getUserAddresses(@PathVariable String id) {
		List<AddressRest> returnValue = new ArrayList<>();

		List<AddressDto> addressesDto = addressService.getAddresses(id);

		if (addressesDto != null && !addressesDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressRest>>() {
			}.getType();
			returnValue = new ModelMapper().map(addressesDto, listType);
		}
		return returnValue;
	}

	@GetMapping(path = "/{userId}/addresses/{addressId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<AddressRest> getUserAddressById(@PathVariable String userId, @PathVariable String addressId) {
		AddressRest returnValue = new AddressRest();

		AddressDto addressesDto = addressService.getAddressById(addressId);

		if (addressesDto != null) {
			returnValue = new ModelMapper().map(addressesDto, AddressRest.class);
		}

		// HATEOAS Links
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");
		Link userAddressesLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");

		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddressById(userId, addressId))
				.withSelfRel();

		return EntityModel.of(returnValue, Arrays.asList(userAddressesLink, userLink, selfLink));
	}

}