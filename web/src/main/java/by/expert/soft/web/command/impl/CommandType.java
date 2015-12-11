package by.expert.soft.web.command.impl;

import by.expert.soft.web.command.impl.commands.ImportCommand;
import by.expert.soft.web.command.impl.commands.PageImportCommand;
import by.expert.soft.web.command.impl.commands.ViewDataCommand;
import by.expert.soft.web.command.interf.Command;

public enum CommandType {
    IMPORT_FILE {
        @Override
        public Command getCommand() {
            return new ImportCommand();
        }
    },
    VIEW_DATA {
        @Override
        public Command getCommand() {
            return new ViewDataCommand();
        }
    },
    PAGE_IMPORT {
        @Override
        public Command getCommand() {
            return new PageImportCommand();
        }
    };

    public abstract Command getCommand();
}
