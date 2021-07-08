/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Automatically decompile all functions and export to .c file
// One file for every function
//@author Salvatore Ansani <salvatoreansani@gmail.com>
//@category Decompile
//@keybinding 
//@menupath 
//@toolbar 

import ghidra.app.script.GhidraScript;
import ghidra.program.model.util.*;
import ghidra.program.model.reloc.*;
import ghidra.program.model.data.*;
import ghidra.program.model.block.*;
import ghidra.program.model.symbol.*;
import ghidra.program.model.scalar.*;
import ghidra.program.model.mem.*;
import ghidra.program.model.listing.*;
import ghidra.program.model.lang.*;
import ghidra.program.model.pcode.*;
import ghidra.program.model.address.*;
import ghidra.app.script.GhidraScript;
import ghidra.program.model.listing.Function;
import ghidra.app.decompiler.DecompInterface;
import ghidra.app.decompiler.DecompiledFunction;
import java.io.PrintWriter;
import java.io.File;

public class MyDecompileScript extends GhidraScript {

	@Override
	public void run() throws Exception {

		File outputDirectory = askDirectory("Select the path for output files","Select");

		var program = this.getCurrentProgram();
		DecompInterface ifc = new DecompInterface();
		ifc.openProgram(program);
		int count=0;
		for(var func : program.getFunctionManager().getFunctions(true)) {
			count++;
			String string = count + "  :  " + func.getName() + " @ " + func.getEntryPoint();
			monitor.setMessage(string);
		        DecompiledFunction myFunc = ifc.decompileFunction(func, 100000, monitor).getDecompiledFunction();
		        PrintWriter out = new PrintWriter(outputDirectory.getAbsolutePath()+"/"+func.getName()+".c");
			out.write(myFunc.getC());
			out.close();
		}

}
