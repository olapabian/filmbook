package com.filmbook.model.others;

import com.filmbook.jsons.InfoRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoAndBool {
    private InfoRequest infoRequest;
    private boolean flag;

    public boolean getFlag() {
        return  flag;
    }
}
