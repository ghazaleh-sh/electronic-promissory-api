package ir.co.sadad.promissory.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.dtos.*;
import ir.co.sadad.promissory.dtos.promissory.*;
import ir.co.sadad.promissory.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ir.co.sadad.promissory.commons.Constants.OTP;
import static ir.co.sadad.promissory.commons.Constants.SSN;

@RestController
@RequiredArgsConstructor
public class PromissoryController {

   
}

