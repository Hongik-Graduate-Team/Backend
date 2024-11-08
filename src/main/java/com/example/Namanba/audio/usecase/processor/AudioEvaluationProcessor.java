package com.example.Namanba.audio.usecase.processor;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.UniversalAudioInputStream;
import be.tarsos.dsp.util.fft.FFT;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;

import com.example.Namanba.audio.dto.response.AudioEvaluationDto;
import org.springframework.web.multipart.MultipartFile;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.util.fft.FFT;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;


import java.io.InputStream;

public class AudioEvaluationProcessor {
//    public AudioEvaluationDto evaluateAudio(MultipartFile audioFile) {
//
//
//    }
}
