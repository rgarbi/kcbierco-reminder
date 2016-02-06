package com.kcbierco;

import com.google.inject.AbstractModule;
import com.kcbierco.mailer.EmailService;
import com.kcbierco.mailer.impl.EmailServiceImpl;


/**
 * Created by Richard on 2/2/16.
 */
public class ServiceInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(EmailService.class).to(EmailServiceImpl.class);
    }
}
