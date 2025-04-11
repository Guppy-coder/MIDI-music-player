package com.midiPlayer;

import javax.sound.midi.*;

public class MusicTest1 {

    public void play(int instrument, int note)  {

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            System.out.println("We got a sequencer");
            sequencer.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);

            Track track = seq.createTrack();

            ShortMessage first = new ShortMessage();
            first.setMessage(192, 1, instrument, 100);
            MidiEvent changeInstrument = new MidiEvent(first, 1);

            track.add(changeInstrument);

            ShortMessage b = new ShortMessage();
            b.setMessage(144, 1, note, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            sequencer.setSequence(seq);
            sequencer.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        if (args.length < 2){
            System.out.println("Don't forget to set the instrument and note");
        }else {
            int instrument = Integer.parseInt(args[0]);
            int note = Integer.parseInt(args[1]);
            MusicTest1 musicTest1 = new MusicTest1();
            musicTest1.play(instrument, note);
        }


    }
}
