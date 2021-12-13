package by.itransition.chikanoff.payloads.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SimpleMessage implements Serializable {
    private String name;
}
