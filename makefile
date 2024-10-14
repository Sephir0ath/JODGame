.PHONY: clean

TARGET_EXEC ?= ./run

SOURCE_DIR = ./
OUTPUT_DIR = output

SRCS := $(shell find $(SOURCE_DIR) -name "*.cpp")
OBJS := $(SRCS:%=$(OUTPUT_DIR)/%.o)
DEPS := $(OBJS:.o=.d)

INCLUDE_DIRS := $(shell find $(SOURCE_DIR) -type d) /usr/include/SDL2  # Agregar SDL2 headers
INCLUDE_FLAG := $(addprefix -I,$(INCLUDE_DIRS))

C++ ?= g++

COMPILER_VERS ?= -std=c++20
COMPILER_FLAG ?= $(COMPILER_VERS) $(INCLUDE_FLAG) -MMD -MP

# Agregar flag de enlazado de SDL2
LINKER_FLAG ?= -lSDL2

MKDIR ?= mkdir -p

$(OUTPUT_DIR)/$(TARGET_EXEC): $(OBJS)
	@echo $(OBJS)
	$(C++) $(COMPILER_FLAG) -o $@ $(OBJS) $(LINKER_FLAG)  # Enlazar SDL2

$(OUTPUT_DIR)/%.cpp.o: %.cpp
	$(MKDIR) $(dir $@)
	$(C++) $(COMPILER_FLAG) -o $@ -c $<

clean:
	$(RM) -r $(OUTPUT_DIR)

-include $(DEPS)
