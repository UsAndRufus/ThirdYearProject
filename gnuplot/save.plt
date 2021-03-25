# Based on http://people.duke.edu/~hpgavin/gnuplot.html
# File name: save.plt - save a Gnuplot plot as a PostScript/PNG file
# to use:
#  gnuplot>   load 'save.plt'
#  gnuplot>   !mv my-plot.ps another-file.ps
set size 1.0, 0.6
#set terminal postscript portrait enhanced mono dashed lw 1 "Helvetica" 14
set terminal png large
set output "my-plot.png"
replot
set terminal windows
set size 1,1

#      set terminal postscript {<mode>} {enhanced | noenhanced}
#                              {color | colour | monochrome}
#                              {blacktext | colortext | colourtext}
#                              {solid | dashed} {dashlength | dl <DL>}
#                              {linewidth | lw <LW>}
#                              {<duplexing>}
#                              {"<fontname>"} {<fontsize>}


#     set terminal gif {transparent} {interlace}
#                       {tiny | small | medium | large | giant}
#                       {size <x>,<y>}
#                       {<color0> <color1> <color2> ...}

#     set terminal png
#             {{no}transparent} {{no}interlace}
#             {tiny | small | medium | large | giant}
#             {font <face> {<pointsize>}}
#             {size <x>,<y>} {{no}crop}
#             {{no}enhanced}
#             {<color0> <color1> <color2> ...}


