package com.kopyn.cqrs.account_service.api.commands;

public sealed interface TransactionContinuationCommand
        permits ContinueTransactionCommand, AbortTransactionCommand {}
