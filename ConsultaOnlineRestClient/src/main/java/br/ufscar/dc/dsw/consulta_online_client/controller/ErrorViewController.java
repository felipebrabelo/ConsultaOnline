package br.ufscar.dc.dsw.consulta_online_client.controller;

import java.util.Map;

import org.springframework.boot.webmvc.autoconfigure.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ErrorViewController implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView view = new ModelAndView("error");
        view.addObject("status", status.value());

        switch (status.value()) {
        case 403:
            view.addObject("error", "403.error");
            view.addObject("message", "403.message");
            break;
        case 404:
            view.addObject("error", "404.error");
            view.addObject("message", "404.message");
            break;
        default:
            view.addObject("error", "default.error");
            view.addObject("message", "default.message");
            break;
        }

        return view;
    }
}
